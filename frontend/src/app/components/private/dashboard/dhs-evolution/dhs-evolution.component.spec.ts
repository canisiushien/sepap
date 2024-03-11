import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DhsEvolutionComponent } from './dhs-evolution.component';

describe('DhsEvolutionComponent', () => {
  let component: DhsEvolutionComponent;
  let fixture: ComponentFixture<DhsEvolutionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DhsEvolutionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DhsEvolutionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
