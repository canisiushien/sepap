import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailProgrammationComponent } from './detail-programmation.component';

describe('DetailProgrammationComponent', () => {
  let component: DetailProgrammationComponent;
  let fixture: ComponentFixture<DetailProgrammationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DetailProgrammationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailProgrammationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
