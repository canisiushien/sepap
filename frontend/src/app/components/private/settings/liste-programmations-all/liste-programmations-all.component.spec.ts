import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeProgrammationsAllComponent } from './liste-programmations-all.component';

describe('ListeProgrammationsAllComponent', () => {
  let component: ListeProgrammationsAllComponent;
  let fixture: ComponentFixture<ListeProgrammationsAllComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListeProgrammationsAllComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListeProgrammationsAllComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
