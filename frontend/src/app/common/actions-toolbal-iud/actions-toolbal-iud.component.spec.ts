import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActionsToolbalIudComponent } from './actions-toolbal-iud.component';

describe('ActionsToolbalIudComponent', () => {
  let component: ActionsToolbalIudComponent;
  let fixture: ComponentFixture<ActionsToolbalIudComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ActionsToolbalIudComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ActionsToolbalIudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
